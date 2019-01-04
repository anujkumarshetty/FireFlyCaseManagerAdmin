import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Dsheadertemplate from './dsheadertemplate';
import DSFooterTemplate from './ds-footer-template';
import DSCorrespondence from './ds-correspondence';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/dsheadertemplate`} component={Dsheadertemplate} />
      <ErrorBoundaryRoute path={`${match.url}/ds-footer-template`} component={DSFooterTemplate} />
      <ErrorBoundaryRoute path={`${match.url}/ds-correspondence`} component={DSCorrespondence} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
