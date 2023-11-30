import { configureStore, createSlice } from "@reduxjs/toolkit";

//useState랑 비슷한 용도, state하나를 slice라고 부름
let user = createSlice({
    name : 'user',  // state이름
    initialState : 'kim'  // state 값
})

let stock = createSlice({
    name : 'stock',
    initialState : [10,11,12]
})

let postId = createSlice({
    name : 'postId',
    initialState: { id:0},

    reducers : {
        changeId(state,action){
            console.log(state)
            console.log(action.payload)
            return { ...state, id: action.payload };
        }
    }
})

export let {changeId} = postId.actions
export default configureStore({
    reducer: {
        userInfo : user.reducer,
        stock : stock.reducer,
        postId : postId.reducer
    }
})