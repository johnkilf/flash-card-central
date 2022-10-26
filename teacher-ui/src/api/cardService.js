import axios from 'axios';

export default axios.create({
    baseURL: `${process.env.CARD_URI ? process.env.CARD_URI : 'http://localhost'}:8100`,
    headers: {}
});