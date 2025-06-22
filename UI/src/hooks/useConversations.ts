import { useQuery } from "@tanstack/react-query";

export default function useConversations(){
    const {isLoading,isSuccess,isError, data} = useQuery({
        queryKey:["/conversationIds"],
        refetchInterval: 30*1000,
        queryFn: ()=> fetch("http://localhost:8089/v1/conversations").then(r => r.json())
    });
    return {isLoading,isSuccess,isError,data}
}