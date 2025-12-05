import './assets/css/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import { router } from '@/router'

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons'
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-bootstrap.css';
import FloatingVue from 'floating-vue'
import 'floating-vue/dist/style.css'
import timeago from 'vue-timeago3'

library.add(faEye, faEyeSlash)

const app = createApp(App)

app.use(router)

app.use(timeago, {
    converterOptions: {
        includeSeconds: true
    }
})

app.use(FloatingVue, {
    themes: {
        'info-tooltip': {
            $extend: 'tooltip',
            distance: 32,
        },
    },
})

app.use(VueToast as any)

app.component('font-awesome-icon', FontAwesomeIcon)

app.mount('#app')
