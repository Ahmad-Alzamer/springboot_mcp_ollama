import { useEffect, useState } from "react";

export type setConversationId = (val: string)=>void;
export type useConversationIdType = {conversationId: string, setConversationId: setConversationId, newConversation: ()=>void};

export function useConversationId(): useConversationIdType{
    const SET_SESSION_STORAGE_KEY  = 'conversationId';
    const [conversationIdResult, setConversationIdResult] = useState<string>('');
    useEffect(()=>{
      let conversationId = sessionStorage.getItem(SET_SESSION_STORAGE_KEY);
      if(!conversationId){
        conversationId = crypto.randomUUID();
        sessionStorage.setItem(SET_SESSION_STORAGE_KEY,conversationId);
      }
      setConversationIdResult(conversationId)
    },[setConversationIdResult])

    const  setConversationId: setConversationId= (val)=>{
        sessionStorage.setItem(SET_SESSION_STORAGE_KEY,val)
        setConversationIdResult(val);
    }
    const newConversation = ()=>{
      const conversationId = crypto.randomUUID();
      sessionStorage.setItem(SET_SESSION_STORAGE_KEY,conversationId);
      setConversationIdResult(conversationId)
    }
  
    return {conversationId: conversationIdResult,setConversationId: setConversationId, newConversation};
  }

