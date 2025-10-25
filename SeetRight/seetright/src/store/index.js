import {createStore} from 'vuex'

const store = createStore({
    state: {
        count: 2,
        todos: [
            {
                id: 1,
                text: 'todo1',
                done: true
            },
            {
                id: 2,
                text: 'todo2',
                done: false
            }
        ]
    },
    getters: {
        doneTodos(state) {
            return state.todos.filter(todo => todo.done)
        }
    },
    mutations: {
        increment(state) {
            state.count++;
        }
    }
})

export default store
