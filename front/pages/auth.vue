<template>
  <div class="auth">
    <div class="auth__header"></div>
    <div class="auth__banner">
      <img 
        class="auth__illustration"
        :src="store.getImageUrl('icons/logo.svg')"
      />
      <p class="auth__welcome-text">Добро пожаловать <br> в МТС Пространства</p>
    </div>
    <div class="auth__buttons">
      <Button 
        class="auth__button"
        mode="primary"
        text="Войти"
        @press="() => {openAuthPopup('SignIn')}"
      />
      <Button 
        class="auth__button"
        mode="secondary"
        text="Зарегистрироваться"
        @press="() => {openAuthPopup('SignUp')}"
      />
    </div>
    <Popup
      class="auth__popup"
      :isActive="popup.isActive"
      @close="popup.isActive = false"
    >
      <div class="auth__popup-content">
        <h2 class="auth__popup-title">{{ popup.title }}</h2>
        <Input 
          name="Никнейм"
          size="lg"
          @type="(text) => {state.username = text}"
          :value="state.username"
        />
        <Input 
          name="Почта"
          v-if="popup.mode === 'SignUp'"
          size="lg"
          @type="(text) => {state.login = text}"
          :value="state.login"
        />
        <Input 
          name="Пароль"
          size="lg"
          @type="(text) => {state.password = text}"
          :value="state.password"
        />
        <Button 
          mode="primary"
          text="Далее"
          @press="(e) => {popup.mode === 'SignIn' ? signIn() : signUp()}"
        />
        <p class="hint">Не получается войти</p>
      </div>
    </Popup>
  </div>
</template>

<script setup lang="ts">

const store = useGeneralStore()
const router = useRouter()

const popup = reactive({
  isActive: false,
  title: '',
  mode: 'SignIn'
})

const state = reactive({
  username: '',
  password: '',
  login: ''
})

const openAuthPopup = (mode : 'SignIn' | 'SignUp') => {
  popup.isActive = true
  popup.title = mode === 'SignIn' ? 'Войдите' : 'Зарегистрируйтесь'
  popup.mode = mode
} 

const signIn = async () => {
  const res = await fetch(`${store.httpServer}/auth/sign-in`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify({
      username: state.username,
      password: state.password,
    })
  })
  const data = await res.json()
  localStorage.token = data.token
  if (res.status === 200) {
    localStorage.username = state.username
    router.push('/')
  }
}

const signUp = async () => {
  const res = await fetch(`${store.httpServer}/auth/register`, {
    method: 'POST',
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify({
      email: state.login,
      password: state.password,
      username: state.username
    })
  })
  const data = await res.json()
  if (res.status === 200) {
    signIn()
  }
}

</script>

<style scoped lang="scss">
.auth {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  grid-column: 1 / -1;
}
.auth__banner {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
}
.auth__illustration {
  width: 100px;
}
.auth__welcome-text {
  font-size: 1.25rem !important;
  text-align: center;
}
.auth__buttons {
  display: flex;
  flex-direction: column;
  gap: .75rem;
}
.auth__popup {
  
}
.auth__popup-title {
  margin-bottom: 1rem;
}
.auth__popup-content {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  .hint {
    align-self: center;
  }
}
</style>