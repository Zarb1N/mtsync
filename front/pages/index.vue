<template>
  <div class="home">
    <h1>Главная</h1>
    <h2>Рекомендации для вас</h2>
    <div class="home__sessions">
      <SessionCard 
        class="home__session"
        v-for="(session, index) in sessionStore.allSessions"
        :content="(session as Session)"
        :coverId="index + 1"
        @click="$router.push(`/sessions/${session.sessionId}`)"
      />
    </div>

    <h2>Ваши пространства</h2>
    
    <div class="home__my-sessions">
      <img 
        :src="store.getImageUrl(`images/cover1.png`)"
      />
      <h3>Наслаждайтесь музыкой вместе</h3>
      <p>Создавай пространства, приглашай друзей и слушай новинки с самыми близкими</p>
    </div>

    <h2>Пространства друзей</h2>
    <div class="home__friends">
      <p class="home__friends-hint hint">Пригласите друзей и наслаждайтесь аудио вместе</p>
      <Button 
        text="Пригласить друзей"
        @click="state.isPopup = true"
      />
    </div>

    <Popup 
      class="home__invite-popup"
      :isActive="state.isPopup"
      @close="state.isPopup = false"
    >
      <div class="home__invite-popup-content">
        <h2 class="home__invite-popup-title">Поделитесь приложением!</h2>
        <Input 
          :name="'Ссылка'"
          size="lg"
          :value="currentPath"
        />
        <Button 
          text="Поделиться"
          mode="primary"
        />
      </div>
    </Popup>
    
  </div>
</template>

<script setup lang="ts">
const sessionStore = useSessionStore()
import { Session } from 'openvidu-browser';
const store = useGeneralStore()

const state = reactive({
  isPopup: false
})

const currentPath = window.location.href

</script>


<style scoped lang="scss">
.home {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  position: relative;
  padding-bottom: 70px;
}
.home__sessions {
  margin-left: -20px;
  margin-right: -20px;
  padding: 0 20px;
  max-width: 100vw;
  overflow-x: auto;
  display: flex;
  gap: 1rem;
  &::-webkit-scrollbar {
    display: none;
  }
}
h2 {
  margin-top: 20px;
}
.home__session {
  min-width: 20rem ;
}
.home__friends {
  min-height: 136px;
  border-radius: 16px;
  box-shadow: 0px 4px 16px 0px rgba(0, 0, 0, 0.08), 0px 0px 16px 0px rgba(0, 0, 0, 0.08);
  padding: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  &-hint {
    font-size: 17px !important;
  }
}
.home__invite-popup {
  position: absolute;
  z-index: 100;
  &-content {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
}

.home__my-sessions {
  display: flex;
  flex-direction: column;
  gap: 4px;
  img {
    max-width: 100%;
  }
}
</style>