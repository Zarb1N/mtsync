<template>
  <div 
    :class="[
      'input',
    ]"
  >
    <label 
      class="input__label hint"
      v-if="props.name"
    >{{ props.name }}</label>
    <div 
      :class="[
        'input__field',
        `input__field--${size}`,
      ]">
      <img 
        class="input__icon"
        v-if="props.icon"
        :src="store.getImageUrl(`icons/${props.icon}.svg`)"
      />
      <input 
        class="input__input"
        type="text"
        :placeholder="props.placeholder"
        :value="props.value"
        @input="(event : any) => {$emit('type', event.target?.value, event)}"
      />
    </div>

  </div>
</template>

<script setup lang="ts">

const store = useGeneralStore()
const props = defineProps({
  icon: String,
  placeholder: String,
  name: String,
  size: {
    type: String,
    default: 'md'
  },
  value: String
})


</script>

<style scoped lang="scss">
.input {
  display: flex;
  flex-direction: column;
  gap: .5rem;
}
.input__field {
  background: var(--background-light-secondary);
  border-radius: 10px;
  display: flex;
  align-items: center;
  gap: .5rem;
  &--md {
    padding: 6px 12px;
  }
  &--lg {
    padding: 10px 12px;
  }
}
.input__input {
  width: 100%;
  background: none;
  border: none;
}
.input__input::placeholder {
  font-family: 'MTS Sans Regular' !important;
}
</style>