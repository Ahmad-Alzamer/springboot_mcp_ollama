import { createFileRoute } from '@tanstack/react-router'
import ChatPage from '../pages/ChatPage';



export const Route = createFileRoute('/chat')({
  component: GenericChat
})

function GenericChat(){
  return <ChatPage endpoint='chat' title='Generic'/>
}