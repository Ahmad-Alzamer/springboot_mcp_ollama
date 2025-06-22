import { createFileRoute } from '@tanstack/react-router'
import ChatPage from '../pages/ChatPage';



export const Route = createFileRoute('/springBootChat')({
  component: GenericChat
})

function GenericChat(){
  return <ChatPage endpoint='chat/spring' title='Spring/SpringBoot'/>
}