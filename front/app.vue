<template>
  <div :class="[
    'index',
    `index--${layoutType}`
    ]"
  >
    <NavBar 
      class="index__navigation"
      :items="navItems"
      :mode="layoutType"
      v-if="state.isTabbar"
    />
    <RouterView
      class="index__subpage"
    />
  </div>
</template>

<script lang="ts" setup>
import '~/assets/style.css'
import { useGeneralStore } from './stores/general';
import { computed, onMounted } from 'vue';

const store = useGeneralStore()
const sessionStore = useSessionStore()
const route = useRoute()
const router = useRouter()
const musicStore = useMusicStore()

const state = reactive({
  isTabbar: true
})

interface navItem {
  icon: string,
  name: string,
  path: string,
}

const navItems : navItem[] = [
  {
    icon: 'house',
    name: 'Главная',
    path: '/',
  },
  {
    icon: 'magnifier',
    name: 'Поиск',
    path: '/sessions',
  },
  {
    icon: 'stack',
    name: 'Создание',
    path: '/sessions/create',
  },
  {
    icon: 'person',
    name: 'Еще',
    path: '/tools',
  },
]



const layoutType = computed(() => {
  const mobileSizes : size[] = ['xs', 'sm', 'md']
  return mobileSizes.includes(store.viewportWidthType) ? 'mobile' : 'desktop' 
})


const getWindowSize = () => {
	store.viewport.width = document.documentElement.clientWidth;
	store.viewport.height = document.documentElement.clientHeight;
}

onMounted(() => {
	getWindowSize()
	window?.addEventListener('resize', e => {
		getWindowSize()
	})
})

const restrictedRoutes = ['/auth', ]

onMounted(() => {
  checkIsTabbar()
})

router.afterEach( (to, from) => {
  checkIsTabbar(to.path)
  if (!localStorage.token && to.fullPath !== '/auth') {
    router.push('/auth')
  }
})

const checkIsTabbar = (path = route.path) => {
  state.isTabbar = !restrictedRoutes.includes(path)
  if (
    route.path.includes('sessions') 
    && route.path.split('/')[2]?.includes('id')
    && ['xs', 'sm', 'md'].includes(store.viewportWidthType)) {
    state.isTabbar = false
  }
}

sessionStore.getAllSessions()
sessionStore.getAllOvSessions()

const checkUserAuthorization = async () => {
  if (localStorage.token) {
    const res = await fetch(`${store.httpServer}/checkAuth`, {
      headers: store.httpHeaders
    })
    if (res.status !== 200) {
      router.push('/auth')
    }
  } else {
    router.push('/auth')
  }
}

checkUserAuthorization()
musicStore.getPlaylists()
musicStore.getTracks()

</script>


<style scoped lang="scss">

.index {
  padding: 10px 20px 80px 20px;
  min-height: 100vh;
  display: flex;
  .index__navigation {
    background-color: white;
    z-index: 100;
  }
  .index__subpage {
    width: 100%;
  }
}

.index--mobile {
  .index__navigation {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100vw;
  } 
  padding-bottom: 3rem;
}

.index--desktop {
  display: grid;
  grid-template-columns: 15rem 1fr 15rem;
  gap: 2rem;
  .index__subpage {
    display: flex;
    flex-direction: column;
  }
  .index__navigation {
    height: min-content;
  }
}

</style>