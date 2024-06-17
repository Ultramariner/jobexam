import axios from 'axios';

/**
 * Получает приветственное сообщение
 */
export async function searchWelcomeMessage() {
        return (await axios.get(`welcome/message` )).data;
}