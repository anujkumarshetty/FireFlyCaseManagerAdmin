import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './dsheadertemplate.reducer';
import { IDsheadertemplate } from 'app/shared/model/dsheadertemplate.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDsheadertemplateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDsheadertemplateUpdateState {
  isNew: boolean;
}

export class DsheadertemplateUpdate extends React.Component<IDsheadertemplateUpdateProps, IDsheadertemplateUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { dsheadertemplateEntity } = this.props;
      const entity = {
        ...dsheadertemplateEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/dsheadertemplate');
  };

  render() {
    const { dsheadertemplateEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { headercontent } = dsheadertemplateEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="fireFlyCaseManagerAdminApp.dsheadertemplate.home.createOrEditLabel">Create or edit a Dsheadertemplate</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : dsheadertemplateEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="dsheadertemplate-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="headercontentLabel" for="headercontent">
                    Headercontent
                  </Label>
                  <AvInput
                    id="dsheadertemplate-headercontent"
                    type="textarea"
                    name="headercontent"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="headertemplatenameLabel" for="headertemplatename">
                    Headertemplatename
                  </Label>
                  <AvField
                    id="dsheadertemplate-headertemplatename"
                    type="text"
                    name="headertemplatename"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/dsheadertemplate" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  dsheadertemplateEntity: storeState.dsheadertemplate.entity,
  loading: storeState.dsheadertemplate.loading,
  updating: storeState.dsheadertemplate.updating,
  updateSuccess: storeState.dsheadertemplate.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DsheadertemplateUpdate);
