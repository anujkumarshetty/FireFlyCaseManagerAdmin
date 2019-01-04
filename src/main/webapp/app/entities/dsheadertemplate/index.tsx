import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Dsheadertemplate from './dsheadertemplate';
import DsheadertemplateDetail from './dsheadertemplate-detail';
import DsheadertemplateUpdate from './dsheadertemplate-update';
import DsheadertemplateDeleteDialog from './dsheadertemplate-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DsheadertemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DsheadertemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DsheadertemplateDetail} />
      <ErrorBoundaryRoute path={match.url} component={Dsheadertemplate} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DsheadertemplateDeleteDialog} />
  </>
);

export default Routes;
