import api from "@/utils/axios-auth";
import {ref} from "vue";
export function useEvent() {

    const loading = ref(false);
    const error = ref('');

    const getEvents = async () => {
        try {
            loading.value = true;
            error.value = '';
            const response = await api.get('events/query/user');
            return response.data;

        } catch (err) {
            if (err.response) {
                error.value = err.response.data.error
            }
        } finally {
            loading.value = false;
        }
    };

    const getEvent = async (id) => {
        try {
            loading.value = true;
            error.value = '';
            const response = await api.get(`events/${id}`);
            return response.data;
        } catch (err) {
            if (err.response) {
                error.value = err.response.data.error;
            }
        } finally {
            loading.value = false;
        }
    };
    return {
        loading,
        error,
        getEvent,
        getEvents
    }
}