import { faSpinner } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useQuery } from "@tanstack/react-query";

export default function AboutPage() {
    const {isLoading,isSuccess,data} = useQuery({queryKey:["/about"], queryFn: ()=>fetch("http://localhost:8089/actuator/info").then(r => r.json())});
    if(isLoading){
      return <div> still loading Info <FontAwesomeIcon icon={faSpinner} spin /></div>
    }
    if(!isSuccess){
      return <div> failed to load</div>
    }
    if(isSuccess)
    return <pre className='has-text-left'>{JSON.stringify(data, null, 2)}</pre>
  }
  