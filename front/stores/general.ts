import { defineStore } from 'pinia'

export const useGeneralStore = defineStore('general', {
	state: () => ({
		viewport: {
			width: 0 as number,
			height: 0 as number,
		},
		wsServer: "wss://mtsync.javastripters.ru/api/ws",
		httpServer: "https://mtsync.javastripters.ru/api",
		httpHeaders: {
			'Content-type': 'application/json',
			'Authorization': `Bearer ${localStorage.token}` 
		}
	}),
	actions: {
		getImageUrl: (path : string) => {
      const fullPath =  new URL(`../assets/${path}`, import.meta.url).href
      return fullPath
    },
		debounce (callback : any, delay : number) {
			let timer : any
			console.log("debounce")
			return (arg : any) => {
				if (timer) clearTimeout(timer)
				timer = setTimeout(
					() => {
						console.log(arg)
						return callback(arg)
					},
					delay
				)
			}
		}
	},
  getters: {
    viewportWidthType: (state) : size => {
			const screenWidthTypes : {[key in size]: [number, number]}= {
				'xs': [320, 479],
				'sm': [480, 767],
				'md': [768, 999],
				'lg': [1000, 1447],
				'xl': [1448, +Infinity]
			}
      // @ts-ignore
			const screenWidthType = Object.keys(screenWidthTypes).find( (key : size) => {
				return (
					screenWidthTypes[key][0] <= state.viewport.width 
					&& state.viewport.width <= screenWidthTypes[key][1]
				)
			})
			return screenWidthType as size
		},
		
  }
})



