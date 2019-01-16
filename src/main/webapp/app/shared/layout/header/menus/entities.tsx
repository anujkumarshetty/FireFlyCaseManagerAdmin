import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Templates" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/dsheadertemplate">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Header Template
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/ds-footer-template">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Footer Template
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/ds-correspondence">
      {/* <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Ds Correspondence */}
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Letter Template
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
