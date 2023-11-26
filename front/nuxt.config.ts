// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  ssr: false,
  target: 'static',
  modules: [
    //'@nuxt/content',
    '@pinia/nuxt',
    '@nuxt/devtools'
  ],
  devServer: {
    port: 3000
  },
  app: {
    head: {
      meta: [
        { name: 'viewport', content: 'width=device-width, initial-scale=1' }
      ],
      noscript: [
        { children: 'Javascript is required' }
      ]
    },
		// pageTransition: { name: 'page', mode: 'out-in' }
  },
})
