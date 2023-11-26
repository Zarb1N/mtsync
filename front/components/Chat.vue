<template>
  <div class="chat">
    <img 
      class="chat__close-btn"
      :src="store.getImageUrl('icons/chevron.dark-gray.svg')"
      @click="$emit('close')"
    />
    <ul class="chat__messages">
      <li 
        class="chat__message message"
        v-for="message in props.messages"
      >
        <div class="message__avatar"></div>
        <div class="message__sender-name">{{ message.senderName }}</div>
        <div class="message__text">{{ message.text }}</div>
      </li>
    </ul>
    <textarea 
      class="chat__textarea"
      v-model="state.textareaContent"
      placeholder="Введите сообщение"
    />
    <div
      class="chat__send-btn"
      @click="() => {$emit('send', state.textareaContent); state.textareaContent = ''}"
    >
      <img :src="store.getImageUrl(`icons/arrow.white.svg`)"/>
    </div>
  </div>
</template>

<script setup lang="ts">

const store = useGeneralStore()

const props = defineProps({
  messages: Array
})

const state = reactive({
  textareaContent: ''
})


</script>

<style scoped lang="scss">
.chat {
  position: relative;
  display: grid;
  grid-template-columns: 1fr 2rem;
  grid-template-rows: 1fr min-content;
  align-items: center;
  gap: .5rem;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.78) 0%, #FFF 67.71%);
  backdrop-filter: blur(1.9500000476837158px);
  padding: 12px 20px;
  align-items: flex-end;

}
.chat__messages {
  grid-column: 1 / -1;
  display: flex;
  flex-direction: column;
  gap: 20px;
  overflow-y: auto;
  overflow-x: hidden;
  max-height: 100%;
  &::-webkit-scrollbar {
    display: none;
  }
}
.message {
  display: grid;
  grid-template-columns: 44px 1fr;
  column-gap: 24px;
}
.message__avatar {
  grid-row: 1 / 3;
  height: 44px;
  width: 44px;
  background-color: tomato;
  border-radius: 50%;
}
.message__sender-name {
  font-size: 14px;
  font-weight: 900;
}
.chat__textarea {
  border-radius: 16px;
  border: 1px solid var(--background-light-stroke, rgba(188, 195, 208, 0.50));
  background: var(--background-light-secondary, #F2F3F7);
  padding: 4px 12px;
  line-height: 1.5rem;
  height: 2rem;
  resize: none;
  &::-webkit-scrollbar {
    display: none;
  }
}
.chat__send-btn {
  background-color: var(--controls-light-primary-active);
  height: 2rem;
  width: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 1rem;
}
.chat__close-btn {
  position: absolute;
  top: 0;
  right: 10px;
  padding: 10px;
  rotate: 180deg;
}
</style>