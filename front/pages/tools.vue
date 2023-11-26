<template>
  <div>
    <Button 
      mode="primary"
      text="Выйти"
      @press="logOut()"
    />
  </div>

</template>

<script setup lang="ts">
const router = useRouter()

const logOut = () => {
  localStorage.clear()
  router.push('/auth')
}

</script>

<style scoped>

</style>