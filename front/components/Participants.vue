<template>
  <div 
    :class="[
      'users',
      `users--${store.viewportWidthType}`
    ]"
    
  >
    <hr>
    <div class="users__header">
      <img 
        :class="[
          'users__show-btn',
          `users__show-btn--${true ? 'up' : 'down'}`
        ]"
        :src="store.getImageUrl('icons/chevron.dark-gray.svg')"
        @click="$emit('toggle')"
      />
      <h3 class="users__title">Участники ({{ 0 }})</h3>
      <img 
        :class="['users__mode-btn',]"
        :src="store.getImageUrl(`icons/${state.displayMode === 'tile' ? 'list' : 'tiles'}.dark-gray.svg`)"
        @click="state.displayMode = state.displayMode === 'tile' ? 'list' : 'tile'"
      />
    </div>
    <hr>
    <div 
      :class="[
        'users__participants',
        `users__participants--${state.displayMode}`
      ]">
      <Participant 
        class="user__participant"
        v-for="sub in sessionStore.subscribers" 
        :key="sub.stream.connection.connectionId" 
        :stream-manager="sub"
        :mode="state.displayMode"
        @click.native="sessionStore.updateMainVideoStreamManager(sub)" 
      />
    </div>
  </div>
</template>

<script setup lang="ts">

const store = useGeneralStore()
const sessionStore = useSessionStore()

type displayMode = 'tile' | 'list'

const state = reactive({
  displayMode: 'tile'
})

</script>

<style scoped lang="scss">
.users {
  .users__header {
    display: grid;
    grid-template-columns: 2rem 1fr 2rem;
    align-items: center;
  }
  .users__participants--tile {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(151px, 1fr));
    gap: 1rem;
    min-height: 150px;
  }
  .users__participants--list {
    display: flex;
    flex-direction: column;
    gap: 20px;
  }
}

.users--xl,
.users--lg {

}

.users--md,
.users--sm,
.users--xs {
  max-width: 100%;
  .users__participants {
    width: 100%;
  }

}

</style>