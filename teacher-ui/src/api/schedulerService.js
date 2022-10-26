import axios from 'axios';

export default axios.create({
    baseURL: `${process.env.CARD_SCHEDULING_URI ? process.env.CARD_SCHEDULING_URI : 'http://localhost'}:8200`,
    headers: {}
});