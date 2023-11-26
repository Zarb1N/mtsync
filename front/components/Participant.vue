<template>
<div 
	:class="[
		'participant',
		`participant--${mode}`
	]"
	v-if="streamManager"
>
	<div 
		class="participant__avatar"
		v-if="mode === 'list'"
	></div>
	<ov-video 
		class="participant__video"
		v-if="mode === 'tile'"
		:streamManager="streamManager"
	/>
	<p class="participant__name">{{ clientData }}</p>
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


<style scoped lang="scss">
.participant, .participant * {
	max-width: 100%;
	width: 100%;

}
.participant {
	position: relative;
	overflow: hidden;
}
.participant__video {
}

.participant--tile {
	.participant__video {
		border-radius: 1rem;
	}
}

.participant--list {
	display: grid;
	grid-template-columns: 44px 1fr;
	gap: 12px;
	align-items: center;
}
.participant__avatar {
	width: 44px;
	height: 44px;
	background-color: tomato;
	border-radius: 4px;
}
</style>