<template>
  <div class="header">
    <div 
      class="header__leave-btn  header__btn"
      @click="$emit('leave')"
    >
      <img 
        :src="store.getImageUrl('icons/back.dark-gray.svg')"
      />
    </div>
    <div class="header__info">
      <div class="header__cover"></div>
      <h4 class="header__title">{{ name }}</h4>
      <p class="header__song-name">{{ song }}</p>
    </div>
    <div class="header__options header__btn">
      <img 
        :src="store.getImageUrl('icons/dots.dark-gray.svg')"
      />
    </div>
  </div>
</template>

<script setup lang="ts">

const store = useGeneralStore()

const props = defineProps({
  name: String,
  song: String
})

</script>

<style scoped>
.header {
  display: grid;
  grid-template-columns: 2rem 1fr 2rem;
  gap: .5rem;
  align-items: center;
}
.header__info {
  display: grid;
  grid-template-columns: 2rem 1fr;
  grid-template-rows: 19px 12px;
  column-gap: 5px;
}
.header__cover {
  grid-row: 1 / 3;
  height: 2rem;
  width: 2rem;
  border-radius: 5px;
  background-color: tomato;
}
.header__title {
  line-height: 20px !important;
}
.header__btn {
  height: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
.header__song-name {
  line-height: 12px !important;
}


</style>