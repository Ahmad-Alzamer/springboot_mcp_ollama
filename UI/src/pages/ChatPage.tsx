import { useState } from "react";
import { useConversationId, type setConversationId } from "../hooks";
import MDEditor from "@uiw/react-md-editor";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSpinner } from "@fortawesome/free-solid-svg-icons";
import useConversations from "../hooks/useConversations";
import Markdown from "react-markdown";

type ChatPageProps = {endpoint: 'chat'| 'chat/playwrightExpert' | 'chat/spring', title: string}

export default function ChatPage({endpoint,title}:ChatPageProps) {
    const [messages,setMessages] = useState<{context:string, owner: 'user'|'ai'}[]>([]);
    const [prompt,setPrompt] = useState<string>();
    const [isThinking, setThinking] = useState<boolean>(false);
    const {conversationId, setConversationId, newConversation} = useConversationId();
    
  
  
    const onClickHanlder= (event: React.MouseEvent<HTMLButtonElement>)=>{
      console.debug("clicked",event.currentTarget.textContent,", \nprompt:",prompt);
      processPrompt()
    }
  
    const processPrompt = async ()=>{
      if(!prompt){
        alert("please provide prompt text");
        return;
      }
      setThinking(true);
      setMessages(m => [...m, {context: prompt, owner:'user'}])
      setPrompt(undefined);
      
      try{
        const searchParams = new URLSearchParams();
        searchParams.append("prompt",prompt);
        searchParams.append("conversationId",conversationId);
        console.info("searchParams",searchParams.toString());
        const response = await fetch(`http://localhost:8089/v1/${endpoint}?${searchParams}`);
        const message = await response.text();
        setMessages(m => [...m, {context: message, owner:'ai'}])
      }catch(e){
        console.error(e);
      }finally{
        setThinking(false);
      }
    }
  
    const onChangeHandler = (value?: string)=>{
      setPrompt(value??'');
    }
  
    
  
    return (
      <div className='box'>
        <h2 className="subtitle">{title}</h2>
        <PreviousConversationsInput setConversationId={setConversationId}/>
        <button className="button is-primary is-outlined is-rounded mb-2" onClick={newConversation}>New Conversation</button>
  
        <div className='fixed-grid has-12-cols ' id="messages">
          <div className='grid '>
            {messages.map(m => <div key={m.context} className={`box cell is-col-span-8 ${m.owner==='ai'? "has-background-primary-light has-text-primary-light-invert has-text-left": "has-background-primary has-text-primary-invert is-col-start-5 has-text-right"}`}><Markdown>{m.context}</Markdown></div>) }
          </div>
        </div>
        <div className='columns'>
        </div>
        <div className='columns'>
          <MDEditor
            value={prompt}
            onChange={onChangeHandler}
            className='column is-full is-rows-span-3' 
          />
        </div>
        <div className='box columns'>
          <button className={`button ${!isThinking? "is-primary" : " is-light is-outlined"} column is-half is-offset-one-quarter`} onClick={onClickHanlder} disabled={isThinking}> {isThinking? <FontAwesomeIcon icon={faSpinner} spin />:<></>} submit</button>
        </div>
        
      </div>
    )
  }
  
  function PreviousConversationsInput({setConversationId}:{setConversationId : setConversationId}){
    const previousConversations = useConversations();
    const onChangeHandler = (event: React.ChangeEvent<HTMLSelectElement>)=>{
      console.info("continuing previous conversation:",event.target.value);
      setConversationId(event.target.value);
    }
    return <div className='fixed-grid has-12-cols ' id="previous-conversations">
          {previousConversations.isSuccess && <div className='select is-rounded'>
            <select onChange={onChangeHandler} >
              <option >Select a previous conversation to continue</option>
              {previousConversations.data.map((c:any) => <option key={c}>{c}</option>)}
            </select>
          </div>}
        </div>      
  }
  
  
  
  