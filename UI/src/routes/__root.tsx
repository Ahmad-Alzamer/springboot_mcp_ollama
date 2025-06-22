import { createRootRoute, Outlet } from '@tanstack/react-router'
import { TanStackRouterDevtools } from '@tanstack/react-router-devtools'
import { CustomLink } from '../components/CustomLink'
import { faComments, faQuestionCircle } from '@fortawesome/free-regular-svg-icons'
import { faHome } from '@fortawesome/free-solid-svg-icons'

export const Route = createRootRoute({
  component: () => (
    <>
    <div className="tabs is-medium is-centered is-boxed">
        <ul>
            <CustomLink to='/' activeProps={{isActive:true}} icon={faHome} >Home</CustomLink>
            <CustomLink to='/chat' activeProps={{isActive:true}} icon={faComments}>Chat</CustomLink>
            <CustomLink to='/springBootChat' activeProps={{isActive:true}} icon={faComments}>SpringBoot Chat</CustomLink>
            <CustomLink to='/about' activeProps={{isActive:true}} icon={faQuestionCircle}>About</CustomLink>
        </ul>
    </div>
      <h1 className="title">AI Chat Client</h1>
      <Outlet />
      <TanStackRouterDevtools />
    </>
  ),
})
