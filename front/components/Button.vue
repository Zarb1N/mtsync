<template>
  <div
    :class="[
      'button',
      `button--${props.mode}`
    ]"
    @click="(e) => {$emit('press', e)}"
  >
    <img 
      class="button__icon"
      v-if="props.icon"
      :src="props.icon"
    >
    <h3 class="button__text">{{ props.text }}</h3>
  </div>
</template>

<script setup lang="ts">

const props = defineProps({
  text: String,
  mode: {
    type: String,
    default: 'secondary'
  },
  icon: {
    type: String,
    default: ''
  }
})

</script>

<style scoped>
.button {
  padding: 10px .75rem;
  border-radius: .5rem;
  display: flex;
  justify-content: center;
  gap: .5rem;
  cursor: pointer;
}
.button--primary {
  background-color: var(--controls-light-primary-active);
  color: var(--main-light);
}
.button--secondary {
  background-color: var(--secondary);
  color: var(--main-dark);
}

</style>