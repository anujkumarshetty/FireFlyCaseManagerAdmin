import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './ds-correspondence.reducer';
import { IDSCorrespondence } from 'app/shared/model/ds-correspondence.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDSCorrespondenceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDSCorrespondenceUpdateState {
  isNew: boolean;
}

export class DSCorrespondenceUpdate extends React.Component<IDSCorrespondenceUpdateProps, IDSCorrespondenceUpdateState> {
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
      const { dSCorrespondenceEntity } = this.props;
      const entity = {
        ...dSCorrespondenceEntity,
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
    this.props.history.push('/entity/ds-correspondence');
  };

  render() {
    const { dSCorrespondenceEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { lettertemplate, lettertemplateContentType } = dSCorrespondenceEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="fireFlyCaseManagerAdminApp.dSCorrespondence.home.createOrEditLabel">Create or edit a DSCorrespondence</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : dSCorrespondenceEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="ds-correspondence-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="templateidLabel" for="templateid">
                    Templateid
                  </Label>
                  <AvField id="ds-correspondence-templateid" type="string" className="form-control" name="templateid" />
                </AvGroup>
                <AvGroup>
                  <Label id="lettertypeLabel" for="lettertype">
                    Lettertype
                  </Label>
                  <AvField
                    id="ds-correspondence-lettertype"
                    type="text"
                    name="lettertype"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="categoryLabel" for="category">
                    Category
                  </Label>
                  <AvField
                    id="ds-correspondence-category"
                    type="text"
                    name="category"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="subcategoryLabel" for="subcategory">
                    Subcategory
                  </Label>
                  <AvField
                    id="ds-correspondence-subcategory"
                    type="text"
                    name="subcategory"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="lettertemplateLabel" for="lettertemplate">
                      Lettertemplate
                    </Label>
                    <br />
                    {lettertemplate ? (
                      <div>
                        <a onClick={openFile(lettertemplateContentType, lettertemplate)}>Open</a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {lettertemplateContentType}, {byteSize(lettertemplate)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('lettertemplate')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_lettertemplate" type="file" onChange={this.onBlobChange(false, 'lettertemplate')} />
                    <AvInput
                      type="hidden"
                      name="lettertemplate"
                      value={lettertemplate}
                      validate={{
                        required: { value: true, errorMessage: 'This field is required.' }
                      }}
                    />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="isactiveLabel" for="isactive">
                    Isactive
                  </Label>
                  <AvField id="ds-correspondence-isactive" type="string" className="form-control" name="isactive" />
                </AvGroup>
                <AvGroup>
                  <Label id="parentidLabel" for="parentid">
                    Parentid
                  </Label>
                  <AvField id="ds-correspondence-parentid" type="string" className="form-control" name="parentid" />
                </AvGroup>
                <AvGroup>
                  <Label id="templatetypeLabel" for="templatetype">
                    Templatetype
                  </Label>
                  <AvField
                    id="ds-correspondence-templatetype"
                    type="text"
                    name="templatetype"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ds-correspondence" replace color="info">
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
  dSCorrespondenceEntity: storeState.dSCorrespondence.entity,
  loading: storeState.dSCorrespondence.loading,
  updating: storeState.dSCorrespondence.updating,
  updateSuccess: storeState.dSCorrespondence.updateSuccess
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
)(DSCorrespondenceUpdate);
