<template>
  <input 
    type="range" 
    step="1"
    min="0"
    :max="props.max"
    :value="props.value"
    :style="`background-size: ${state.backgroundSize}% 100%`"
    @input="(e) => {handleInput(e)}"
  />
</template>

<script setup lang="ts">

const context = getCurrentInstance()
const store = useGeneralStore()

const props = defineProps({
  max: {
    type: Number,
    default: 0
  },
  value: {
    type: Number,
    default: 0
  },
})

const state = reactive({
  value: 0,
  backgroundSize: 0
})

const emitData = (time : number) => {
  console.log(time)
  context?.emit('customInput', time)
}

const handleInput = (event : InputEvent) => {
  const time = event.target?.value
  state.value = time
  state.backgroundSize =  state.value / props.max * 100
  emitData(time)
}


watch(() => props.value, (value) => {
  state.value = value
  state.backgroundSize = state.value / props.max * 100
})


</script>

<style scoped lang="scss">


input[type="number"] {
  width: 40px;
  padding: 4px 5px;
  border: 1px solid #bbb;
  border-radius: 3px;
}

/* input[type="range"]:focus,
input[type="number"]:focus {
  box-shadow: 0 0 3px 1px #4b81dd;
  outline: none;
} */

input[type="range"] {
  -webkit-appearance: none;
  margin-right: 15px;
  width: 100%;
  height: 3px;
  background: #9E9E9E;
  border-radius: 5px;
  background-image: linear-gradient(#252525, #252525);
  // background-size: 70% 100%;
  background-repeat: no-repeat;
}

[dir="rtl"] input[type="range"] {
  background: #ff4500;
  background-image: linear-gradient(#9E9E9E, #9E9E9E);
  // background-size: 30% 100%;
  background-repeat: no-repeat;
}

/* Input Thumb */
input[type="range"]::-webkit-slider-thumb {
  -webkit-appearance: none;
  height: 7px;
  width: 7px;
  border-radius: 50%;
  background: #252525;
  cursor: grabbing;
  box-shadow: 0 0 2px 0 #555;
  transition: background .3s ease-in-out;
  transition: all .3s;
}

/*
input[type="range"]::-moz-range-thumb {
  -webkit-appearance: none;
  height: 20px;
  width: 20px;
  border-radius: 50%;
  background: #ff4500;
  cursor: ew-resize;
  box-shadow: 0 0 2px 0 #555;
  transition: background .3s ease-in-out;
}

input[type="range"]::-ms-thumb {
  -webkit-appearance: none;
  height: 20px;
  width: 20px;
  border-radius: 50%;
  background: #ff4500;
  cursor: ew-resize;
  box-shadow: 0 0 2px 0 #555;
  transition: background .3s ease-in-out;
}
*/

input[type="range"]::-webkit-slider-thumb:hover {
  height: 15px;
  width: 15px;
  transition: all .3s;
}

/*
input[type="range"]::-moz-range-thumb:hover {
  background: #ff0200;
}

input[type="range"]::-ms-thumb:hover {
  background: #ff0200;
}
*/

/* Input Track */
input[type=range]::-webkit-slider-runnable-track  {
  -webkit-appearance: none;
  box-shadow: none;
  border: none;
  background: transparent;
}

input[type=range]::-moz-range-track {
  -webkit-appearance: none;
  box-shadow: none;
  border: none;
  background: transparent;
}

input[type="range"]::-ms-track {
  -webkit-appearance: none;
  box-shadow: none;
  border: none;
  background: transparent;
}
</style>