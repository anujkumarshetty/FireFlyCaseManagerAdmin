import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ds-correspondence.reducer';
import { IDSCorrespondence } from 'app/shared/model/ds-correspondence.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDSCorrespondenceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export class DSCorrespondenceDetail extends React.Component<IDSCorrespondenceDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { dSCorrespondenceEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Letter Template [<b>{dSCorrespondenceEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            {/* <dt>
              <span id="templateid">Templateid</span>
            </dt>
            <dd>{dSCorrespondenceEntity.templateid}</dd> */}
            <dt>
              <span id="lettertype">Letter Type</span>
            </dt>
            <dd>{dSCorrespondenceEntity.lettertype}</dd>
            <dt>
              <span id="category">Category</span>
            </dt>
            <dd>{dSCorrespondenceEntity.category}</dd>
            <dt>
              <span id="subcategory">Sub Category</span>
            </dt>
            <dd>{dSCorrespondenceEntity.subcategory}</dd>
            <dt>
              <span id="lettertemplate">Letter Template</span>
            </dt>
            <dd>
              {dSCorrespondenceEntity.lettertemplate ? (
                <div>
                  <a onClick={openFile(dSCorrespondenceEntity.lettertemplateContentType, dSCorrespondenceEntity.lettertemplate)}>
                    Open&nbsp;
                  </a>
                  <span>
                    {dSCorrespondenceEntity.lettertemplateContentType}, {byteSize(dSCorrespondenceEntity.lettertemplate)}
                  </span>
                </div>
              ) : null}
            </dd>
            {/* <dt>
              <span id="isactive">Isactive</span>
            </dt>
            <dd>{dSCorrespondenceEntity.isactive}</dd> */}
            {/* <dt>
              <span id="parentid">Parentid</span>
            </dt>
            <dd>{dSCorrespondenceEntity.parentid}</dd> */}
            <dt>
              <span id="templatetype">Templatetype</span>
            </dt>
            <dd>{dSCorrespondenceEntity.templatetype}</dd>
          </dl>
          <Button tag={Link} to="/entity/ds-correspondence" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/ds-correspondence/${dSCorrespondenceEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ dSCorrespondence }: IRootState) => ({
  dSCorrespondenceEntity: dSCorrespondence.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DSCorrespondenceDetail);
