<template>
  <div 
    :class="[
      'owner',
      `owner--${useGeneralStore().viewportWidthType}`
    ]"
    v-if="streamManager"
  >
    <ov-video 
      class="owner__video"
      :streamManager="streamManager"  
    />
  </div>
</template>

<script>
import OvVideo from './OvVideo';


export default {
  name: 'UserVideo',

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
    isVideo: {
      type: Boolean,
      default: false
    },
    mode: {
      type: String,
      default: 'tile'
    }
  },

  computed: {
    clientData () {
      const { clientData } = this.getConnectionData() || {}
      return clientData;
    },
  },

  methods: {
    getConnectionData () {
      if (this.streamManager.stream) {
        const { connection } = this.streamManager?.stream;
        return JSON.parse(connection.data);
      } else {
        return
      }
    },
  },
};
</script>


<style scoped>
.owner, .owner * {
  max-width: 100%;
  width: 100%;
}
.owner {
  position: relative;
  overflow: hidden;
}
.owner__video {
  
}
.owner--sm,
.owner--md,
.owner--xs {
  max-height: 30vh;
}

</style>