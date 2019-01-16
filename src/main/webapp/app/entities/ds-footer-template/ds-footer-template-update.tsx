import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './ds-footer-template.reducer';
import { IDSFooterTemplate } from 'app/shared/model/ds-footer-template.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import CKEditor from "react-ckeditor-component";

export interface IDSFooterTemplateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export interface IDSFooterTemplateUpdateState {
  isNew: boolean;
  ckEditorContent: string;
  isFlag: boolean
}



const toolbarConfig = {
  height: "250px",
  toolbar: 'Full',
  allowedContent: true,
  startupFocus: true,
  // toolbarGroups: [
  //   { name: 'document', groups: ['mode', 'document', 'doctools'] },
  //   { name: 'clipboard', groups: ['clipboard', 'undo'] },
  //   { name: 'editing', groups: ['find', 'selection', 'spellchecker', 'editing'] },
  //   { name: 'forms', groups: ['forms'] },
  //   { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
  //   { name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph'] },
  //   { name: 'links', groups: ['links'] },
  //   { name: 'insert', groups: ['insert'] },
  //   { name: 'styles', groups: ['styles'] },
  //   { name: 'colors', groups: ['colors'] },
  //   { name: 'tools', groups: ['tools'] },
  //   { name: 'others', groups: ['others'] },
  //   { name: 'about', groups: ['about'] }
  // ],
  // removeButtons: 'PasteFromWord,Image,Source,Save,NewPage,Preview,Templates,Cut,Copy,Paste,PasteText,Find,Replace,SelectAll,Scayt,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Strike,Subscript,Superscript,CopyFormatting,RemoveFormat,Outdent,Indent,Blockquote,CreateDiv,BidiLtr,BidiRtl,Link,Unlink,Anchor,Flash,Smiley,SpecialChar,PageBreak,Iframe,Styles,Maximize,ShowBlocks,About,Language',
  // removePlugins: 'elementspath',
};

export class DSFooterTemplateUpdate extends React.Component<IDSFooterTemplateUpdateProps, IDSFooterTemplateUpdateState> {
  constructor(props) {
    super(props);
    this.saveEntity = this.saveEntity.bind(this);
    this.onChange = this.onChange.bind(this);
    this.state = {
      isFlag: true,
      ckEditorContent: '',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  onChange(evt) {
    console.log("onChange fired with event info: ", evt);
    var newContent = evt.editor.getData();
    this.setState({
      ckEditorContent: newContent,
      isFlag: false
    })
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
      const { dSFooterTemplateEntity } = this.props;
      values.footercontent = this.state.ckEditorContent;
      const entity = {
        ...dSFooterTemplateEntity,
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
    this.props.history.push('/entity/ds-footer-template');
  };

  render() {
    const { dSFooterTemplateEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { footercontent } = dSFooterTemplateEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="fireFlyCaseManagerAdminApp.dSFooterTemplate.home.createOrEditLabel">Create or edit a Footer Template</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
                <AvForm model={isNew ? {} : dSFooterTemplateEntity} onSubmit={this.saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="id">ID</Label>
                      <AvInput id="ds-footer-template-id" type="text" className="form-control" name="id" required readOnly />
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label id="footertemplatenameLabel" for="footertemplatename">
                      Footer template name
                  </Label>
                    <AvField
                      id="ds-footer-template-footertemplatename"
                      type="text"
                      name="footertemplatename"
                      validate={{
                        required: { value: true, errorMessage: 'This field is required.' }
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="footercontentLabel" for="footercontent">
                      Footer content
                  </Label>
                    <div>
                      {/* {this.state.ckEditorContent} */}
                      <CKEditor
                        activeClass="p10"
                        config={toolbarConfig}
                        content={this.state.isFlag ? dSFooterTemplateEntity.footercontent : this.state.ckEditorContent}
                        events={{
                          "change": this.onChange
                        }}
                      />
                    </div>
                  </AvGroup>

                  <Button tag={Link} id="cancel-save" to="/entity/ds-footer-template" replace color="info">
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
  dSFooterTemplateEntity: storeState.dSFooterTemplate.entity,
  loading: storeState.dSFooterTemplate.loading,
  updating: storeState.dSFooterTemplate.updating,
  updateSuccess: storeState.dSFooterTemplate.updateSuccess
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
)(DSFooterTemplateUpdate);
