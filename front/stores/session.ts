import { defineStore } from "pinia"
import { OpenVidu, Session, Subscriber, ExceptionEvent, Publisher } from "openvidu-browser";
import axios from "axios";

export const useSessionStore = defineStore('session', {
  state: () => ({
    mediaServer: "https://openvidu.thecntgfy.ru/openvidu/api",
    accessToken: "T1BFTlZJRFVBUFA6cGFzcw==",
    OV: null as OpenVidu | null,
    session: null as Session | null,
    mainStreamManager: null as Publisher | null,
    publisher: null as Publisher | null,
    subscribers: [] as Subscriber[] | null,
    allSessions: [] as Session[] | null,
    allOvSessions: [] as Session[] | null,
    mySessionId: '' as string,
    myUserName: localStorage.username || `Участник-${Math.random() * 100}` as string
  }),
  actions: {
    updateMainVideoStreamManager (stream : any) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },
    async getToken(mySessionId : string) {
      this.allSessions = await this.getAllOvSessions()
      const allSessionsIds: Array<string> | undefined = this.allOvSessions?.map( (session : any) => session.id)
      console.log(allSessionsIds, mySessionId)
      const sessionId = allSessionsIds?.includes(mySessionId) ? mySessionId : await this.createSession(mySessionId)
      return await this.createToken(sessionId);
    },
    async createSession(sessionId : string) {
      const response = await axios.post(this.mediaServer + '/sessions', { customSessionId: sessionId }, {
        headers: this.requestHeaders
      });
      console.log(sessionId, response.data.sessionId)
      return response.data.sessionId; // The sessionId
    },
    async createToken(sessionId : string) {
      const response = await axios.post(this.mediaServer + '/sessions/' + sessionId + '/connection', {}, {
        headers: this.requestHeaders
      });
      return response.data.token; // The token
    },
    async getAllSessions() {
      const response = await fetch(`${useGeneralStore().httpServer}` + '/rooms', {
        headers: this.requestHeaders
      })
      const data = await response.json()
      this.allSessions = data
      return data
    },
    async getAllOvSessions() {
      const response = await fetch(`${this.mediaServer}/sessions`, {
        headers: this.requestHeaders
      })
      const data = await response.json()
      this.allOvSessions = data.content
      return data.content
    },
    leaveSession() {
      if (this.session) this.session.disconnect();
      this.session = null ;
      this.mainStreamManager = null;
      this.publisher = null;
      this.subscribers = [];
      this.OV = null;
    },
  },
  getters: {
    requestHeaders() : Record<string, string> {
      return { 
        'Content-Type' : 'application/json',
        'Authorization' : `Basic ${this.accessToken}`
      }
    },
  }
})