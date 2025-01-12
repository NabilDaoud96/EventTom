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
    const getEventsByManager = async ({
                                          page = 0,
                                          size = 10,
                                          sortBy = 'read',
                                          direction = 'asc'
                                      }) => {

        try {
            loading.value = true;
            error.value = '';
            const response = await api.get(`events/manager`, {
                    params: {
                        page,
                        size,
                        sortBy,
                        direction
                    }
                }
            );
            return response.data;
        } catch (err) {
            if (err.response) {
                error.value = err.response.data.error;
            }
        } finally {
            loading.value = false;
        }
    };
    const updateEvent = async (id, event) => {
        try {
            loading.value = true;
            error.value = '';
            const response = await api.put(`events/update/${id}`, event);
            return response.data;
        } catch (err) {
            if (err.response) {
                error.value = err.response.data.message
            }
        } finally {
            loading.value = false;
        }
    };
    return {
        loading,
        error,
        getEvent,
        getEvents,
        getEventsByManager,
        updateEvent
    }
}