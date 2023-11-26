<template>
  <div class="test-page">
    <h1
      class="test-page__title"
    >MTSync</h1>

    <button
      @click="startCapturing()"
    >Capture</button>

    <video 
      class="test-page__player"
      autoplay 
      playsinline 
      ref="video"
      :src="state.video"
    />

    <!--
    <input type="file" accept="image/*;capture=camera">
    <input type="file" accept="video/*;capture=camcorder">
    <input type="file" accept="audio/*;capture=microphone">
    -->

  </div>
</template>

<script setup lang="ts">
import { getCurrentInstance, reactive } from 'vue';


const constraints = {
  audio: true,
  video: {
    width: {
      min: 480,
      ideal: 1280,
      max: 1920
    },
    height: {
      min: 360,
      ideal: 720,
      max: 1080
    }
  }
}

const state = reactive({
  video: ''
})

const startCapturing = async () => {
  try {
    const stream = await navigator.mediaDevices.getUserMedia(constraints);
    processVideoAudio(stream)
  } catch(err) {
    window.alert(`Error was raised: ${err}!`)
  }
}

const context = getCurrentInstance()

const processVideoAudio = (stream : any) => {
  state.video = stream
  const videoElem = context?.refs?.video
  // @ts-ignore
  videoElem.srcObject = stream
  // @ts-ignore
  window.stream = stream
  console.log(videoElem)
  
  // window.alert(stream)
  console.log(stream)
}

</script>


<style scoped>

.test-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;
}
.test-page__title {
  color: red;
}

.test-page__player {
  background-color: red;
  max-width: 70vw;
}
</style>