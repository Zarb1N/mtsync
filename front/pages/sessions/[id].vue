<template>
  <div 
    :class="[
      'session',
      `session--${store.viewportWidthType}`
    ]">
    <SessionHeader 
      class="session__header"
      :name="state.session.name"
      :song="state.session.playlist?.tracks[0]?.name"
      @leave="() => {sessionStore.leaveSession(); $router.push('/sessions')}"
    />
    <Player  
      v-if="state.session?.playlist"
      class="session__player"
      :time="state.audioCurrentTime"
      :isPlaying="state.isAudioActive"
      :data="state.session?.playlist?.tracks[0].fileName"
      @rewind="(time : number) => {update('REWIND', sessionId, time)}"
      @pause="(time : number) => {update('PAUSE', sessionId, time)}"
      @play="(time : number) => {update('PLAY', sessionId, time)}"
    />
    <hr class="session__divider"/>

    <Owner 
      class="session__owner"
      :stream-manager="sessionStore.publisher" 
      @click.native="sessionStore.updateMainVideoStreamManager(sessionStore.publisher)" 
    />


    <!--
      <div id="main-video" class="col-md-6">
        <user-video :stream-manager="sessionStore.mainStreamManager" /> 
      </div>
    -->
    
    <Participants 
      :class="[
        'session__participants',
        `session__participants--${state.isParticipantsOpen ? 'opened' : 'closed'}`
      ]"
      @toggle="state.isParticipantsOpen = !state.isParticipantsOpen"
    />
    <Chat 
      :class="[
        'session__chat',
        `session__chat--${!state.isChat && 'hidden'}`
      ]"
      :messages="state.chatMessages"
      @send="(text) => sendMessage(text)"
      @close="state.isChat = false"
    />
    <Toolbar 
      class="session__toolbar"
      @chat="state.isChat = true"
    />
  </div>
</template>

<script setup lang="ts">
import { useSessionStore } from '~/stores/session';
import { OpenVidu, Session, Subscriber, ExceptionEvent, Publisher } from "openvidu-browser";

const sessionStore = useSessionStore()
const store = useGeneralStore()
// const context = getCurrentInstance()
const route = useRouter().currentRoute.value
const musicStore = useMusicStore()

const state = reactive({
  isVideo: true,
  chatMessages: [],
  audioCurrentTime: 0,
  isAudioActive: false,
  isParticipantsOpen: false,
  isChat: false,
  session: {} as any,
})

 
const joinSession = async (sessionId : string) => {
  sessionStore.OV = new OpenVidu();
  sessionStore.session = sessionStore.OV.initSession();
  sessionStore.session.on("streamCreated", ({ stream }) => {
    const subscriber = sessionStore.session?.subscribe(stream, undefined);
    sessionStore.subscribers?.push(subscriber as Subscriber);
    getCurrentAudioTime()
  });
  sessionStore.session.on("streamDestroyed", ({ stream }) => {
    const index = sessionStore.subscribers?.indexOf(stream.streamManager as Subscriber, 0);
    if (index && index >= 0) {
      sessionStore.subscribers?.splice(index, 1);
    }
  });
  sessionStore.session.on("exception", ({ exception } : any) => {
    console.warn(exception);
  });
  sessionStore.getToken(sessionId).then((token) => { // TODO
    sessionStore.session?.connect(token, { clientData: sessionStore.myUserName })
      .then(() => {
        let publisher = sessionStore.OV?.initPublisher(undefined, {
          audioSource: undefined, // The source of audio. If undefined default microphone
          videoSource: undefined, // The source of video. If undefined default webcam
          publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
          publishVideo: true, // Whether you want to start publishing with your video enabled or not
          resolution: "640x480", // The resolution of your video
          frameRate: 30, // The frame rate of your video
          insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
          mirror: false, // Whether to mirror your local video or not
        });
        sessionStore.mainStreamManager = publisher as Publisher;
        sessionStore.publisher = publisher as Publisher;
        sessionStore.session?.publish(sessionStore.publisher as Publisher);
      })
      .catch((error) => {
        console.log("There was an error connecting to the session:", error.code, error.message);
      });
  });
  sessionStore.session?.on('signal:my-chat', (event) => {
    const senderName = JSON.parse(event.from?.data)?.clientData
    state.chatMessages.push({
      text: event.data,
      senderName,
    })
  });
}

const sessionId : ComputedRef<string> = computed(() => route.params.id as string)

const sendMessage = async (text : string) => {
  sessionStore.session?.signal({
      data: text,                 // Any string (optional)
      to: [],                     // Array of Connection objects (optional. Broadcast to everyone if empty)
      type: 'my-chat'             // The type of message (optional)
    })
    .then(() => {
        console.log('Message successfully sent');
    })
    .catch(error => {
        console.error(error);
    });
}

const getCurrentAudioTime = async () => {
  const res = await fetch(`${store.httpServer}/sessions/${sessionId.value}`, {
    headers: store.httpHeaders
  }) 
  const data = await res.json()
  if (res.status === 200) {
    rewind(data.trackTsMs, sessionId.value)
  }
}

onMounted(() => {
  joinSession(route.params.id as string)  
})

onUnmounted(() => {
  sessionStore.leaveSession()
})

const getSession = async () => {
  const res = await fetch(`${store.httpServer}/rooms/${sessionId.value}`, {
    headers: store.httpHeaders
  })
  const data = await res.json()
  state.session = data
}

getSession()


/* ===== StompJS ===== */

interface audio {
  event: "REWIND" | "PLAY" | "PAUSE",
  sessionId: string,
  trackTsMs: number,
  clientTs: number
}

import { Client } from '@stomp/stompjs';
const client = new Client({
  brokerURL: store.wsServer,
  onConnect: () => {
    client.subscribe(`/user/${route.params.id}/queue/sync`, message => {
      const data : audio = JSON.parse(message.body)
      console.log(data)
      if (data.event === "REWIND" && data.trackTsMs) {
        rewind(data.trackTsMs, data.sessionId)
      } else if (data.event === "PLAY") {
        play()
      } else if (data.event === "PAUSE") {
        pause()
      }
    });
    /*
    client.publish({ destination: '/sync', body: JSON.stringify({
      sessionId: route.params.id,
      trackTsMs: 1000
    }) });
    */
  },
});



client.activate()

const update = (eventType: string, sessionId : string, time : number ) => {
  client.publish({ destination: '/sync', body: JSON.stringify({
      event: eventType,
      sessionId,
      trackTsMs: time * 1000
    }) 
  });
}

const rewind = (time : number, sessionId : string) => {
  state.audioCurrentTime = time / 1000
}
const pause = () => {
  state.isAudioActive = false
}
const play = () => {
  state.isAudioActive = true
}





</script>

<style scoped lang="scss">
.session {
  max-width: 1440px;
}
.session__header {
  margin-bottom: 20px;
}
.session__close-btn {
  height: 2rem;
  padding: 1rem;
  border-radius: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: red;
  cursor: pointer;
}
.session__owner {
  width: 100%;
  margin: 20px 0px;
  border-radius: 1rem;
}

.session--xl,
.session--lg {
  display: grid;
  grid-template-columns: 2fr 1fr;
  column-gap: 2rem;
  max-height: 100vh;
  margin-top: -12px;
  margin-bottom: -40px;
  overflow: hidden;
  padding: 12px 0;
  .session__header {
    grid-column: 1 / 2;
  }
  .session__player {
    grid-column: 2 / 3;
  }
  .session__owner {
    grid-column: 1 / 2;
    height: 60vh;
  }
  .session__participants {
    grid-column: 1 / 2;
    height: 20vh;
  }
  .session__chat {
    grid-column: 2 / 3;
    grid-row: 2 / 10;
  }
  .session__toolbar {
    display: none;
  }
}
.session--xs,
.session--sm,
.session--md {
  display: grid;
  grid-template-rows: repeat(3, min-content) auto;
  .session__header {
    position: sticky;
    top: 15px;
  }
  .session__player {
    position: sticky;
    top: calc(15px + 32px + 15px);
  }
  .session__divider {
    position: sticky;
    top: calc(15px + 32px + 15px + 80px);
  }
  .session__participants {
    position: fixed;
    bottom: 0;
    width: calc(100% - 40px);
    z-index: 10;
    background-color: white;
    transition: height .3s;
    &--opened {
      height: calc(100vh - 134px);
    }
    &--closed {
      height: 50vh;
    }
  }
  .session__chat {
    position: fixed;
    bottom: 0;
    left: 0;
    z-index: 30;
    width: 100vw;
    height: 50vh;
    transition: .3s all;
    &--hidden {
      opacity: 0;
      pointer-events: none;
    }

  }
  .session__toolbar {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100vw;
    border-top: 1px solid #DEE1E8;
    z-index: 20;
  }
}
</style>