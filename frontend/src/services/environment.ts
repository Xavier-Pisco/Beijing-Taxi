import { Topic, type TopicEventMapping } from "@/models/topic";

export class Environment {

  private static readonly websocketUrlDefaults: UrlParts = {
    protocol: 'ws',
    host: 'localhost',
    port: 80,
    path: '',
  }

  private static readonly topicDefaults: TopicEventMapping = {
    [Topic.DRIVING_TAXIS]: 'drivingTaxis',
    [Topic.TAXI_DISTANCES]: 'distances',
    [Topic.SPEEDING_INCIDENTS]: 'speedingIncidents',
    [Topic.AREA_VIOLATIONS]: 'areaViolations',
  }

  private static websocketUrlMemo: string | null = null
  private static eventNamesMemo: TopicEventMapping | null = null

  static get websocketUrl(): string {
    if (this.websocketUrlMemo === null) {
      this.websocketUrlMemo = this.computeWebsocketUrl()
    }
    return this.websocketUrlMemo
  }

  static computeWebsocketUrl(): string {
    const {
      VITE_BACKEND_PROTOCOL_WS,
      VITE_BACKEND_HOST,
      VITE_BACKEND_PORT_WS,
      VITE_BACKEND_PATH_WS,
    } = import.meta.env
    const environmentParts: Partial<UrlParts> = {
      protocol: VITE_BACKEND_PROTOCOL_WS,
      host: VITE_BACKEND_HOST,
      port: Environment.tryParseInt(VITE_BACKEND_PORT_WS),
      path: VITE_BACKEND_PATH_WS,
    }
    const { protocol, host, port, path } = Object.assign({}, Environment.websocketUrlDefaults, environmentParts)
    return `${protocol}://${host}:${port}${path}`
  }

  static get eventNames(): TopicEventMapping {
    if (this.eventNamesMemo === null) {
      this.eventNamesMemo = this.computeEventNames()
    }
    return this.eventNamesMemo
  }

  static computeEventNames(): TopicEventMapping {
    const {
      VITE_WS_TOPIC_DRIVING_TAXIS,
      VITE_WS_TOPIC_TAXI_DISTANCES,
      VITE_WS_TOPIC_SPEEDING_INCIDENTS,
      VITE_WS_TOPIC_AREA_VIOLATIONS,
    } = import.meta.env
    const environmentTopics: Partial<TopicEventMapping> = {
      [Topic.DRIVING_TAXIS]: VITE_WS_TOPIC_DRIVING_TAXIS,
      [Topic.TAXI_DISTANCES]: VITE_WS_TOPIC_TAXI_DISTANCES,
      [Topic.SPEEDING_INCIDENTS]: VITE_WS_TOPIC_SPEEDING_INCIDENTS,
      [Topic.AREA_VIOLATIONS]: VITE_WS_TOPIC_AREA_VIOLATIONS,
    }
    return Object.assign({}, this.topicDefaults, environmentTopics)
  }

  private static tryParseInt(maybeInt: string | undefined): number | undefined {
    if (typeof maybeInt !== 'undefined') {
      const parsedInt = Number.parseInt(maybeInt)
      if(!Number.isNaN(parsedInt)) {
        return parsedInt
      }
    }
  }

}

type UrlParts = {
  protocol: string
  host: string
  port: number
  path: string
}
