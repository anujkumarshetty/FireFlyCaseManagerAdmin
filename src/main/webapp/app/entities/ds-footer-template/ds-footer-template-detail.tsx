import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ds-footer-template.reducer';
import { IDSFooterTemplate } from 'app/shared/model/ds-footer-template.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDSFooterTemplateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DSFooterTemplateDetail extends React.Component<IDSFooterTemplateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { dSFooterTemplateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            DSFooterTemplate [<b>{dSFooterTemplateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="footercontent">Footercontent</span>
            </dt>
            <dd>{dSFooterTemplateEntity.footercontent}</dd>
            <dt>
              <span id="footertemplatename">Footertemplatename</span>
            </dt>
            <dd>{dSFooterTemplateEntity.footertemplatename}</dd>
          </dl>
          <Button tag={Link} to="/entity/ds-footer-template" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/ds-footer-template/${dSFooterTemplateEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ dSFooterTemplate }: IRootState) => ({
  dSFooterTemplateEntity: dSFooterTemplate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DSFooterTemplateDetail);
