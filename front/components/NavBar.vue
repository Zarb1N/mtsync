<template>
  <div :class="[
    'navbar',
    `navbar--${mode}`
  ]">
    <ul class="navbar__buttons">
      <li 
        :class="[
          'navbar__button',
          'button',
          `button--${router.currentRoute.value.path == item.path ? 'choosed' : 'unchoosed'}`
        ]"
        v-for="item in (props.items)"
        :key="item.name + item.path"
        @click="router.push(item.path)"
      >
        <img 
          class="button__icon"
          :src="store.getImageUrl(`icons/${item.icon}.${router.currentRoute.value.path == item.path ? 'red' : 'light-gray'}.svg`)"
        />
        <div class="button__name">{{ item.name }}</div>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
const router = useRouter()
const route = useRoute()
const store = useGeneralStore()
const props = defineProps({
  items: Array<Object>,
  mode: String,
})

const iconColor = computed(() => {
  return 'light-gray'
})

</script>

<style scoped lang="scss">

.navbar {
  .button {
    list-style: none;
    cursor: pointer;
    transition: color .3s;
    &--choosed {
      color: var(--controls-light-primary-active);
    }
    &--unchoosed {
      color: var(--tabbar-gray, #AEB5BD);
    }
  }
  .button__icon {
    height: 28px;
    width: 28px;
  }

}

.navbar--mobile {
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.25);

  .navbar__buttons {
    display: flex;
    align-items: center;
    justify-content: space-evenly;
  }
  .button {
    padding: 1rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: .5rem;
  }
  .button__icon {
    height: 2rem;
    width: 2rem;
    border-radius: .5rem;
  }
}

.navbar--desktop {
  padding: 2rem;
  border-radius: 2rem;
  .navbar__buttons {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
  }
  .button {
    font-size: 1.5rem;
    display: grid;
    grid-template-columns: 2rem 1fr;
    align-items: center;
  }
  .button__icon {
    padding-bottom: 0px;
  }
  .button__name {
    font-family: "MTS Sans Medium";
    font-size: 17px;
    font-style: normal;
    line-height: 28px; /* 164.706% */
  }
  
}

</style>