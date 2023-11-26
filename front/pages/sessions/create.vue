<template>
  <div 
    :class="[
      'create',
      `create--${store.viewportWidthType}`
    ]">
    <h1>Создание</h1>
    <Input 
      placeholder="Поиск"
    />
    <div class="sessions__create create-session section">
      <div class="create-session__card">
        <Button 
          text="Создать пространство без аудио"
          mode="primary"
          @click="createSession(null)"
        />
      </div>
    </div>

    <Tabs 
      class="create__tabs"
      :items="tabsItems"
      :choosed="state.choosedTab"
      @choose="(tab) => {state.choosedTab = tab}"
    />
    <div class="create__playlists">
      <SoundCard 
        v-for="(playlist, index) in musicStore.playlists"
        :content="playlist"
        :coverId="index + 1"
        @click="() => {openPopup(playlist)}"
      />
    </div>
    <Popup
      class="create__options"
      :isActive="state.isSettings"
      @close="() => {state.isSettings = false}"
    >
      <div class="create__options-body">
        <h2>Основные настройки</h2>
        <Input 
          size="lg"
          name="Название"
          :value="state.sessionName"
          @type="(text) => {state.sessionName = text}"
        />

        <Input 
          size="lg"
          name="Отображаемый ник"
          :value="state.username"
        />

        <Button 
          mode="primary"
          @press="createSession(state.choosedPlaylist)"
          text="Создать"
        />
        <h2>Основные настройки</h2>
        <Switcher 
          v-for="switcher in switchers1"
          :content="switcher"
        />
        <h2>Пользовательские настройки</h2>
        <Switcher 
          v-for="switcher in switchers2"
          :content="switcher"
        />
      </div>
    </Popup>
  </div>
</template>

<script setup lang="ts">

const sessionStore = useSessionStore()
const router = useRouter()
const store = useGeneralStore()
const musicStore = useMusicStore()

const tabsItems = [
  {
    text: 'Плейлисты'
  },
  {
    text: 'Музыка'
  }
]

const switchers1 = [
  {
    title: 'Подключиться с видео',
    description: 'Делитесь вашим творчеством в прямом эфире МТС Пространства',
    isChoosed: true
  },
  {
    title: 'Вертикальное видео',
    description: 'По умолчанию ваше видео будет вертикальным',
    isChoosed: false
  },
]
const switchers2 = [
  {
    title: 'Разрешить доступ к микрофону',
    description: 'Пользователи смогут общаться в голосовом чате',
    isChoosed: true
  },
  {
    title: 'Разрешить доступ к видео',
    description: 'Пользователи смогут общаться в видеочате',
    isChoosed: true
  },
  {
    title: 'Закрытое пространство',
    description: 'Если у Вас закрытое пространство, то его смогут увидеть только приглашенные по ссылке пользователи',
    isChoosed: false
  },
]

const state = reactive({
  isSettings: false,
  choosedTab: tabsItems[0],
  choosedPlaylist: null as any,
  sessionName: '',
  username: localStorage.username
})

const openPopup = (playlist : any) => {
  if (playlist) {
    state.choosedPlaylist = playlist
    state.sessionName = playlist.name
  }
  state.isSettings = true
}

const createSession = async (playlistId : number | null) => {
  const sessionId = `id${Date.now()}`
  const res = await fetch(`${store.httpServer}/rooms`, {
    method: 'POST',
    headers: store.httpHeaders,
    body: JSON.stringify({
      sessionId: `id${Date.now()}`,
      name: state.sessionName,
      previewUrl: "string",
      playlistId: state.choosedPlaylist?.id
    })
  })
  const data = await res.json()
  router.push(`/sessions/${sessionId}`)
}

</script>

<style scoped lang="scss">
.create {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
.create__playlists {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
.create__tabs {
  margin-left: -1rem;
  margin-right: -1rem;
}
.create__options {
  z-index: 100;
  position: absolute;

}
.create__options-body {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  max-height: 90vh;
  overflow: auto;
  &::-webkit-scrollbar {
    display: none;
  }
}

.create--xs,
.create--sm,
.create--md {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.create--lg,
.create--xl {

}
</style>