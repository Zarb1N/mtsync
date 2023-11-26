

export const useMusicStore = defineStore('music', {
  state: () => ({
    playlists: [],
    tracks: [],
    trackName: ""
  }),
  actions: {
    async getPlaylists () {
      const store = useGeneralStore()
      const res = await fetch(`${store.httpServer}/music/playlists`, {
        headers: store.httpHeaders
      })
      this.$state.playlists = await res.json()
    },
    async getTracks () {
      const store = useGeneralStore()
      const res = await fetch(`${store.httpServer}/music/tracks`, {
        headers: store.httpHeaders
      })
      this.$state.tracks = await res.json()
    }
  }
})