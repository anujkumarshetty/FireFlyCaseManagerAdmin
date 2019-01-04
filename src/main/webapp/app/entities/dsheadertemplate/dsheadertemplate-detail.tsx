import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './dsheadertemplate.reducer';
import { IDsheadertemplate } from 'app/shared/model/dsheadertemplate.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDsheadertemplateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DsheadertemplateDetail extends React.Component<IDsheadertemplateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { dsheadertemplateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Dsheadertemplate [<b>{dsheadertemplateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="headercontent">Headercontent</span>
            </dt>
            <dd>{dsheadertemplateEntity.headercontent}</dd>
            <dt>
              <span id="headertemplatename">Headertemplatename</span>
            </dt>
            <dd>{dsheadertemplateEntity.headertemplatename}</dd>
          </dl>
          <Button tag={Link} to="/entity/dsheadertemplate" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/dsheadertemplate/${dsheadertemplateEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ dsheadertemplate }: IRootState) => ({
  dsheadertemplateEntity: dsheadertemplate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DsheadertemplateDetail);
