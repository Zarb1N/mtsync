<template>
  <div class="tabs">
    <ul class="tabs__items">
      <li 
        :class="[
          'tabs__item',
          `tabs__item--${item.text == choosed.text ? 'choosed' : 'unchoosed'}`,
        ]"
        v-for="item in items"
        v-html="item.text"
        @click="$emit('choose', item)"
      />
    </ul>
  </div>
</template>

<script setup lang="ts">

const props = defineProps({
  items: {
    type: Array,
    default: []
  },
  choosed: {
    type: Object,
    default: {}
  }
})

</script>

<style scoped>
.tabs {
  padding: .25rem 1rem;
}
.tabs__items {
  display: flex;
  gap: 1rem;
}
.tabs__item {
  padding: 6px 0;
  font-weight: 700;
  cursor: pointer;
}

.tabs__item--choosed {
  color: var(--text-light-headline);
  border-bottom: 2px solid var(--controls-light-primary-active);
  /* transition: .5s all; */
}
.tabs__item--unchoosed {
  color: var(--text-light-tertiary);
}
</style>