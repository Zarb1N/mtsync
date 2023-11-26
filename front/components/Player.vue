<template>
  <div class="player">
    <div class="player__slider">
      <Range 
        class="player__input-range"
        type="range" 
        step="1"
        min="0"
        :max="state.duration || 0"
        :value="state.currentTime"
        @customInput="(time) => {rewindInput(time)}"
      />
    </div>
    <p class="player__current-time hint">{{ state.currentTimeString }}</p>
    <div class="player__controls">
      <div class="player__btn player__btn--prev">
        <img 
          class="player__btn-icon"
          :src="store.getImageUrl(`icons/previous.dark-gray.svg`)"
        >
      </div>
      <div 
        class="player__btn player__btn--play"
        @click="(e) => {props.isPlaying ? pause() : play()}"
      >
        <img 
          class="player__btn-icon"
          :src="store.getImageUrl(`icons/play.dark-gray.svg`)"
        >
      </div>
      <div class="player__btn player__btn--next">
        <img 
          class="player__btn-icon"
          :src="store.getImageUrl(`icons/next.dark-gray.svg`)"
        >
      </div>
    </div>
    <div class="player__duration hint">{{ state.durationString }}</div>









    <audio
      class="player__default" 
      v-show="false"
      ref="audio"
      controls
      preload="metadata"
      @play="(e) => {$emit('play', getCurrentAudioTime(e))}" 
      @pause="(e) => {$emit('pause', getCurrentAudioTime(e))}"
      @loadedmetadata="(e) => {[state.duration, state.durationString] = getDuration()}"
      @timeupdate="(e) => {getCurrentAudioTime(e)}"
    >
      <source 
        :src="`${store.httpServer}/sound?name=${data}`" 
        type="audio/ogg"
      >
    </audio>

    
  </div>
</template>

<script setup lang="ts">
const musicStore = useMusicStore()

const store = useGeneralStore()


const props = defineProps({
  time: {
    type: Number,
    default: 0
  },
  isPlaying: {
    type: Boolean,
    default: false
  },
  sessionName: {
    type: String,
    default: "Безымянное пространство"
  },
  data: String
})
const state = reactive({
  currentTime: 0,
  currentTimeString: '00:00',
  duration: 0,
  durationString: '00:00',
})

const context : any = getCurrentInstance()

/*
const getCurrentAudioTime = () => {
  const audioElem : HTMLAudioElement = context.refs.audio || null
  console.log(`Current audio time is ${audioElem.currentTime}`)
  // context.emit('play', audioElem.currentTime)
  return audioElem.currentTime
}
*/

const getCurrentAudioTime = (e : Event) : [number, string] => {
  const audioElem : HTMLAudioElement = context.refs.audio || null
  const currentTime = audioElem.currentTime
  const currentTimeString : string = new Date(currentTime * 1000).toISOString().slice(11, 19);
  state.currentTime = currentTime
  state.currentTimeString = currentTimeString
  return [currentTime, currentTimeString] 
}

const rewindInput = (time : number) => {
  rewind(time)
}

watch(() => state.currentTime, (newValue, oldValue) => {
  if (newValue < oldValue) {
    oldValue - newValue > 1 && rewind(newValue) 
  } else {
    newValue - oldValue > 1 && rewind(newValue)
  }
})

const rewind = (time : number) => {
  context.emit('rewind', time)
}

const play = () => {
  const audioElem : HTMLAudioElement = context.refs.audio || null
  audioElem.play() 
}

const pause = () => {
  const audioElem : HTMLAudioElement = context.refs.audio || null
  audioElem.pause()
}

watch(() => props.time, (time) => {
  const audioElem : HTMLAudioElement = context.refs.audio || null
  audioElem.currentTime = time
  const currentTimeString : string = new Date(time * 1000).toISOString().slice(11, 19);
  state.currentTime = time
  state.currentTimeString = currentTimeString
  // audioElem.play()
})

watch(() => props.isPlaying, (isPlaying) => {
  const audioElem : HTMLAudioElement = context.refs.audio || null
  isPlaying ? audioElem.play() : audioElem.pause()
})

const getDuration = () : [number, string]=> {
  const audioElem : HTMLAudioElement = context.refs.audio || null
  const duration : number = audioElem.duration
  const durationString : string = new Date(duration * 1000).toISOString().slice(11, 19);
  return [audioElem.duration, durationString]
}



</script>

<style scoped lang="scss">

.player {
  display: grid;
  justify-content: space-between;
  grid-template-columns: 1fr 4fr 1fr;
  gap: 7px;
  align-items: center;
}
.player__slider {
  width: 100%;

  grid-column: 1 / -1;
}
.player__input-range {
}

.player__current-time {}
.player__controls {
  justify-self: center;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  max-width: 200px;
}
.player__btn {
  height: 2rem;
  width: 2rem;
  display: flex;
  justify-content: center;
  align-items: center;
  &--play {
    height: 3rem;
    width: 3rem;
    border-radius: 50%;
  }
}

.player__duration {
  justify-self: flex-end;
}

</style>