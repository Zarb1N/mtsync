<template>
  <div>
    <!-- <Teleport to="#default-layout__popup">  -->
      <div 
        :class="[
          'popup__wrapper',
          `popup__wrapper--${(props.isPopup || props.isActive) ? 'visible' : 'hidden'}`,
          `popup__wrapper--${store.viewportWidthType}`
        ]"
        @click="(event) => {$emit('close');}"
      >
        <div 
          class="popup"
          :type="store.viewportWidthType"
          @click="(event : Event) => {event.stopPropagation()}"
        >
          <div class="popup__header">
            <div class="popup__title">{{ props.title }}</div>
            <!--
            <img 
              v-if="false"
              class="popup__close-btn"
              src="~/assets/icons/x-mark.filled.black.svg"
              @click="() => {$emit('close')}"
            />
            -->
          </div>
          <div class="popup__content">
            <slot></slot>
          </div>
        </div>
          <div class="popup__below-content">
            <slot name="below"></slot>
          </div>
      </div>
    <!-- </Teleport> -->
  </div>
</template>

<script setup lang="ts">
import { useGeneralStore } from '~/stores/general'
const store = useGeneralStore()
const props = defineProps({
  isPopup: Boolean,
  isActive: Boolean,
  title: String
})
</script>

<style scoped lang="scss">
.popup__wrapper {
  height: 100vh;
  width: 100vw;
  // background: rgba(0, 0, 0, 0.35);
  position: fixed;
  top: 0;
  left: 0;
  transition: .3s opacity;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: .5rem;
  z-index: 50;
  overflow: hidden;

  .popup {
    position: relative;
  }
  .popup__header {
    min-height: 1.5rem;
    display: grid;
    grid-template-columns: 1fr 1rem;
    gap: .5rem;
    align-items: flex-start;
    margin-bottom: 1rem;
  }
  .popup__close-btn {
    cursor: pointer;
  }
}

.popup__wrapper--xs, 
.popup__wrapper--sm, 
.popup__wrapper--md {
position: fixed;
height: 100vh;
width: 100vw;
transition: .3s all;
  &.popup__wrapper--visible {
    // background: rgba(0, 0, 0, 0.35);
    .popup {
      // transform: translateY(0%);
      margin-bottom: 0;
    }
  }
  &.popup__wrapper--hidden {
    background: rgba(0, 0, 0, 0.0);
    pointer-events: none;
    .popup {
      // transform: translateY(110%);
      margin-bottom: -100vh;
    }

  }
  .popup {
    position: fixed;
    width: 100vw;
    max-height: calc(100vh - 1.5rem);
    height: min-content;
    bottom: 0;
    border-radius: 1.5rem 1.5rem 0 0;
    transition: .3s all;
  }
  .popup__header {
    min-height: 1.5rem;
    margin-bottom: 1rem;
  }
  .popup__title {
    font-size: calc(18rem / 16);
    line-height: 1.5rem;
  }
  .popup__content {
    background-color: white;
    padding: 20px 20px 40px 20px;
    border-radius: 20px 20px 0 0;
    box-shadow: 0px -8px 24px 0px rgba(0, 0, 0, 0.10), 0px -2px 24px 0px rgba(0, 0, 0, 0.08);
  }
  .popup__below-content {
    display: none;
  }
}

.popup__wrapper--lg,
.popup__wrapper--xl {
  background: rgba(0, 0, 0, 0.35);
  &.popup__wrapper--visible {
    opacity: 1;
    pointer-events: all;
  }
  &.popup__wrapper--hidden {
    opacity: 0;
    pointer-events: none;
  }
  .popup {
    min-width: 10rem;
    min-height: 10rem;
  }
  .popup__header {
    height: 2rem;
    display: grid;
    grid-template-columns: 1fr 1rem;
    gap: .5rem;
    align-items: flex-start;
  }
  .popup__title {
    font-size: 1.5rem;
    line-height: 2rem;
  }
  .popup__close-btn {
    position: absolute;
    right: 1.5rem;
    top: 1.5rem;
    background-color: white;
    border-radius: 50%;
  }
  .popup__content {
    background-color: white;
    padding: 20px 40px;
    border-radius: 20px;
  }

}

</style>