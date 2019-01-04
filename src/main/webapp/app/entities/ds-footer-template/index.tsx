import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DSFooterTemplate from './ds-footer-template';
import DSFooterTemplateDetail from './ds-footer-template-detail';
import DSFooterTemplateUpdate from './ds-footer-template-update';
import DSFooterTemplateDeleteDialog from './ds-footer-template-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DSFooterTemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DSFooterTemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DSFooterTemplateDetail} />
      <ErrorBoundaryRoute path={match.url} component={DSFooterTemplate} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DSFooterTemplateDeleteDialog} />
  </>
);

export default Routes;
