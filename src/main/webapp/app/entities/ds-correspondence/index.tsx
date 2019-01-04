import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DSCorrespondence from './ds-correspondence';
import DSCorrespondenceDetail from './ds-correspondence-detail';
import DSCorrespondenceUpdate from './ds-correspondence-update';
import DSCorrespondenceDeleteDialog from './ds-correspondence-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DSCorrespondenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DSCorrespondenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DSCorrespondenceDetail} />
      <ErrorBoundaryRoute path={match.url} component={DSCorrespondence} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DSCorrespondenceDeleteDialog} />
  </>
);

export default Routes;
