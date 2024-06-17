import {defineConfig} from 'vite';
import vue from '@vitejs/plugin-vue';
import eslint from 'vite-plugin-eslint';
import path, {dirname} from 'path'
import {fileURLToPath} from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);
const proxyConfig = {
    target: 'http://localhost:8080',
};

export default defineConfig({
    plugins: [
        vue(),
        {
            ...eslint(),
            apply: 'build'
        }
    ],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src'),
        },
        extensions: ['.vue', '.js', '.ts'],
    },
    server: {
        proxy: {
            '/jobexam': proxyConfig
        }
    },
    build: {
        rollupOptions: {
            input: {
                // точка входа vue в production mode
                main: './indexVue.html',
                // точка входа vue в development mode
                next: './index.html'
            },
            output:{
                //разбивает главный модуль на меньшие кусочки, чтобы не превысить лимит памяти на один chunk
                manualChunks(id) {
                    if (id.includes('node_modules')) {
                        return id.toString().split('node_modules/')[1].split('/')[0].toString();
                    }
                }
            }
        }
    }
});
