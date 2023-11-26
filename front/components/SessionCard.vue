<template>
  <div 
    :class="[
      'session',
      `session--${coverId}`
    ]"
  >

    <p class="session__viewers-count">{{(Math.random() * 15).toFixed(0)}} чел.</p>

    <div class="session__info">
      <h4 class="session__title">{{ props.content?.name  }}</h4>  
      <p class="session__owner hint"> Owner name </p>
    </div>
     
  </div>
</template>

<script setup lang="ts">

const store = useGeneralStore()

const props = defineProps({
  content: Object,
  coverId: Number
})




</script>

<style scoped lang="scss">

.session {
  border-radius: 1rem;
  padding: 1rem 1.25rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  gap: 85px;
  background-size: cover;
  &--1 {background-image: url("~/assets/images/session1.jpg")}
  &--2 {background-image: url("~/assets/images/session2.jpg")}
  &--3 {background-image: url("~/assets/images/session3.jpg")}
  &--4 {background-image: url("~/assets/images/session4.jpg")}
}
.session__viewers-count {
  background-color: white;
  padding: .15rem .5rem .35rem .5rem;
  border-radius: .5rem;
  width: min-content;
  align-self: flex-end;
  color: var(--controls-light-primary-active);
  white-space: nowrap;
  display: flex;
  align-items: center;
  gap: .25rem;
  position: relative;
}
.session__viewers-count::before {
  content: '';
  display: inline-block;
  min-height: 6px;
  min-width: 6px;
  border-radius: 3px;
  background-color: var(--controls-light-primary-active);
  position: relative;
  top: .1rem;
}
.session__info {
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(1.7000000476837158px);
  padding: 10px 1.25rem;
}
.session__title {
}

</style>