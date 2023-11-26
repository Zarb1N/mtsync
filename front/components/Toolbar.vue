<template>
  <div class="toolbar">
    <div class="toolbar__group">
      <img 
        class="toolbar__button"
        :src="store.getImageUrl('icons/coins.light-gray.svg')"
        @click="$emit('')"
      >
      <img 
        class="toolbar__button"
        :src="store.getImageUrl('icons/link.light-gray.svg')"
        @click="$emit('')"
      >
    </div>
    <div class="toolbar__group">
      <img 
        class="toolbar__button"
        :src="store.getImageUrl('icons/chat.light-gray.svg')"
        @click="$emit('chat')"
      >
      <img 
        class="toolbar__button"
        :src="store.getImageUrl('icons/micro.light-gray.svg')"
        @click="$emit('')"
      >
    </div>
  </div>
</template>

<script setup lang="ts">

const store = useGeneralStore()

</script>

<style scoped>
.toolbar {
  background-color: white;
  padding: 12px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.toolbar__group {
  display: flex;
  align-items: center;
  gap: 20px;
}
</style>