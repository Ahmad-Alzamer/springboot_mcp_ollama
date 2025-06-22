import * as React from 'react'
import { createLink, type LinkComponent, type LinkProps } from '@tanstack/react-router'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import type { IconProp } from '@fortawesome/fontawesome-svg-core';



type CustomLinkProps = React.AnchorHTMLAttributes<HTMLAnchorElement> & {activeLinkClassName?: string, isActive?: boolean, icon?: IconProp};


const   _CustomLink = React.forwardRef<HTMLAnchorElement,CustomLinkProps>(
    (props, ref)=>{
        const {activeLinkClassName,isActive, icon, ...remainingProps} = props;
        return <li className={isActive ? activeLinkClassName: ""}>
            <a ref={ref} {...remainingProps} >
                <span>
                    {!!icon &&<FontAwesomeIcon icon={icon} className='pr-2' />}
                    <i >{remainingProps.children}</i>
                </span>
            </a>
        </li>
    }
);

  const CreatedCustomLink = createLink(_CustomLink);


export const CustomLink: LinkComponent<typeof CreatedCustomLink> = (props) => {
    return <CreatedCustomLink   activeProps={{isActive:true}}  activeLinkClassName='is-active' {...props}  />
  }
