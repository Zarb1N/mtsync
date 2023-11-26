<template>
  <div class="track">
    <img 
      class="track__cover"
      :src="store.getImageUrl(`images/playlist${props.coverId || 1}.jpg`)"
    />
    <h4 class="track__name">{{ props.content?.name }}</h4>
    <p class="track__author hint">{{ props.content?.author }}</p>
  </div>
</template>

<script setup lang="ts">
const store = useGeneralStore()
const props = defineProps({
  content: Object,
  coverId: Number
})
</script>

<style scoped>
.track {
  width: 100%;
  display: grid;
  grid-template-columns: 44px 1fr;
  column-gap: .75rem;
  align-items: center;
}
.track__cover {
  grid-row: 1 / 3;
  height: 44px;
  width: 44px;
  border-radius: 8px !important;
  border:none;
}
</style>