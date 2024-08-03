import {createApp} from 'vue'
import App from './App.vue'
import {setLocale} from 'yup';
import router from './router';
import {ru} from 'yup-locales';
import 'primeflex/primeflex.min.css';
import './assets/theme.css';
import PrimeVue from "primevue/config";

setLocale(ru);

createApp(App)
    .use(router)
    .use(PrimeVue)
    .mount('#app')