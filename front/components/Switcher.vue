<template>
  <div class="switcher">
    <h4 class="switcher__title">{{ content?.title }}</h4>
    <p class="switcher__description hint">{{ content?.description }}</p>
    <input 
      class="switcher__checkbox checkbox apple-switch"
      type="checkbox"
      :checked="content?.isChoosed"
    />
  </div>
</template>

<script setup lang="ts">
const props = defineProps({
  content: Object
})
</script>

<style scoped>
.switcher {
  display: grid;
  grid-template-columns: 1fr 52px;
  column-gap: .5rem;
  align-items: center;
}
.switcher__checkbox {
  grid-column: 2 / 3;
  grid-row: 1 / 3;
}

input.apple-switch {
  position: relative;
  -webkit-appearance: none;
  outline: none;
  width: 50px;
  height: 30px;
  background-color: #fff;
  border: 1px solid #D9DADC;
  border-radius: 50px;
  box-shadow: inset -20px 0 0 0 #fff;
}

input.apple-switch:after {
  content: "";
  position: absolute;
  top: 1px;
  left: 1px;
  background: transparent;
  width: 26px;
  height: 26px;
  border-radius: 50%;
  box-shadow: 2px 4px 6px rgba(0,0,0,0.2);
}

input.apple-switch:checked {
  box-shadow: inset 20px 0 0 0 #4ed164;
  border-color: #4ed164;
}

input.apple-switch:checked:after {
  left: 20px;
  box-shadow: -2px 4px 3px rgba(0,0,0,0.05);
}
</style>